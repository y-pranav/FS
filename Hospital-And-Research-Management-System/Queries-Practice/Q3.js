// Question 3: Count how many times each medicine is prescribed
// Goal:
// Return count of each medicine used across all prescriptions.

// Query:


db.prescriptions.aggregate([
  { $unwind: "$medicines" },
  {
    $group: {
      _id: "$medicines.name",
      count: { $sum: 1 }
    }
  },
  {
    $project: {
      _id: 0,
      medicineName: "$_id",
      count: 1
    }
  }
])
