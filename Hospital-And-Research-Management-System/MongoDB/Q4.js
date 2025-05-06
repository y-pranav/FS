/*
Write a MongoDB aggregation query to count how many appointments are booked for 
Inpatients versus Outpatients.


Sample output:
--------------
[
  { totalAppointments: 10, patientType: 'Outpatient' },
  { totalAppointments: 5, patientType: 'Inpatient' }
]


Query Reference:
-------------------
printjson() : JS library function to display the JSON Object data.
    In db.<collection>.find()/aggregate():
    	db -> Refers to the database.
    	<collection> -> Your collection name
	
*/
	
db.appointments.aggregate([
  {
    $lookup: {
      from: "patients",
      localField: "patientId",
      foreignField: "patientId",
      as: "patientInfo"
    }
  },
  {
    $unwind: "$patientInfo"
  },
  {
    $group: {
      _id: "$patientInfo.patientType",
      totalAppointments: {$sum: 1}
    }
  }, 
  {
    $project: {
      _id: 0,
      totalAppointments: 1,
      patientType: "$_id"
    }
  }
])