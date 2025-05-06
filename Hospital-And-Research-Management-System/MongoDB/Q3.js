/*
Write a MongoDB aggregation query to determine how frequently each medicine 
is prescribed. List medicines in descending order of their usage frequency.

Sample output:
--------------
[
  { prescriptionCount: 3, medicineName: 'Atorvastatin' },
  { prescriptionCount: 3, medicineName: 'Hydrocortisone' },
  { prescriptionCount: 3, medicineName: 'Iohexol' }
]

Query Reference:
-------------------
printjson() : JS library function to display the JSON Object data.
    In db.<collection>.find()/aggregate():
    	db -> Refers to the database.
    	<collection> -> Your collection name
	
*/
db.prescriptions.aggregate([
  {
    $unwind: "$medicines"
  },
  {
    $group: {
      _id: "$medicines.medicineId",
      prescriptionCount: { $sum: 1 }
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
      prescriptionCount: 1,
      medicineId: "$_id",
      medicineName: "$medicineInfo.name"
    }
  },
  {
    $sort: { prescriptionCount: -1 }
  }
])
