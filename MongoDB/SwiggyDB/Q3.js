/*
List users who spent more than ₹1000 on the platform.
Display the user's name along with the total amount they spent.
sort them based on amount spent


Sample output:
--------------
[
  { _id: 'U108', totalSpent: 5026, user_name: 'Tejas Sura' },
  { _id: 'U105', totalSpent: 1624, user_name: 'Jagat Dua' }
]



Query Reference:
-------------------
printjson() : JS library function to display the JSON Object data.
    In db.<collection>.find()/aggregate():
    	db -> Refers to the database.
    	<collection> -> Your collection name
	
*/
	
printjson(
    db.Payments.aggregate([
        {
            $lookup: {
                from: "Users",
                localField: "user_id",
                foreignField: "user_id",
                as: "userInfo"
            }  
        },
        {
           $unwind: "$userInfo"  
        },
        {
            $group: {
                _id: "$userInfo.user_id",
                totalSpent: {$sum: "$amount"},
                user_name: {$first: "$userInfo.name"},
            }  
        },
        {
            $sort: {
                "totalSpent": -1
            }
        },
        {
            $match: {totalSpent: {$gt: 1000}}
        }
    ])
)
