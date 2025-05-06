/*
Write a MongoDB aggregation query to display medicines along with their stock
levels and how frequently each medicine has been prescribed. Sort the medicines
by highest usage and lowest stock levels.


Sample output:
--------------
[
  {
    medicineId: 'M101',
    name: 'Atorvastatin',
    stockLevel: 100,
    usageCount: 3
  },
  {
    medicineId: 'M102',
    name: 'Gabapentin',
    stockLevel: 100,
    usageCount: 3
  },
  {
    medicineId: 'M103',
    name: 'Diclofenac',
    stockLevel: 100,
    usageCount: 3
  }
]

Query Reference:
-------------------
printjson() : JS library function to display the JSON Object data.
    In db.<collection>.find()/aggregate():
    	db -> Refers to the database.
    	<collection> -> Your collection name
	
*/


printjson(
    db.prescriptions.aggregate([
        {
            $unwind: "$medicines"
        },
        {
            $group: {
                _id: "$medicines.medicineId",
                usageCount: {$sum: 1}
            }
        },
        {
            $lookup: {
                from: "medicines",
                localField: "_id",
                foreignField: "medicineId",
                as: "medicineInfo"
            }
        }, 
        {
            $unwind: "$medicineInfo"  
        },
        {
            $project: {
                _id: 0,
                medicineId: "$_id",
                name: "$medicineInfo.name",
                stockLevel: "$medicineInfo.stockLevel",
                usageCount: 1
            }
        }, 
        {
            $sort: {
                usageCount: -1,
                stockLevel: 1
            }    
        }
    ])
)























