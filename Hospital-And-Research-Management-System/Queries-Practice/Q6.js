// Question 6: Add a cost category to lab tests
// Goal:
// For each lab test, include a new field costCategory based on cost:

// "Low" if cost < 1000

// "Medium" if cost between 1000 and 2000

// "High" if cost > 2000

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
    $project: {
      _id: 0,
      patientName: "$patientInfo.name",
      testType: "$type",
      cost: "$cost",
      costCategory: {
        $cond: {
          if: { $lt: ["$cost", 1000] },
          then: "Low",
          else: {
            $cond: {
              if: { $lte: ["$cost", 2000] },
              then: "Medium",
              else: "High"
            }
          }
        }
      }
    }
  }
])