/*
Write a MongoDB aggregation query to analyze and summarize how many patients 
of each type (Inpatient and Outpatient) have each kind of insurance coverage 
(Full, Partial, None).
Sort the data by patient-type in ascending orders, and descending by count.

Sample output:
-------------
[
  {
    patientCount: 3,
    patientType: 'Inpatient',
    insuranceCoverage: 'Partial'
  },
  {
    patientCount: 3,
    patientType: 'Outpatient',
    insuranceCoverage: 'Partial'
  }
]


Query Reference:
-------------------
printjson() : JS library function to display the JSON Object data.
    In db.<collection>.find()/aggregate():
    	db -> Refers to the database.
    	<collection> -> Your collection name
	
*/
	
db.patients.aggregate([
    {
        $group: {
            _id: {
                patientType: "$patientType",
                insuranceCoverage: "$insurance.coverage"
            },
            patientCount: {$sum: 1},
        }
    },
    {
        $project: {
            _id: 0,
            patientCount: 1,
            patientType: "$_id.patientType",
            insuranceCoverage: "$_id.insuranceCoverage"
        }
    }
])