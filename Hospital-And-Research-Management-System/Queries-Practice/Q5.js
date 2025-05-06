// Question 5: Filter expensive lab tests with patient info
// Goal:
// Get all lab tests where cost is greater than or equal to 1500 along with patient name.

// Query:

db.lab_tests.aggregate([
  {
    $lookup: {
      from: "patients",
      localField: "patientId",
      foreignField: "patientId",
      as: "patientInfo"
    }
  },
  { $unwind: "$patientInfo" },
  {
    $match: {
      cost: { $gte: 1500 }
    }
  },
  {
    $project: {
      _id: 0,
      patientName: "$patientInfo.name",
      testType: "$type",
      cost: 1
    }
  }
])