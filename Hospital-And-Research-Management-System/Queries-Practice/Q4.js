// Question 4: Sum billing total for each patient
// Goal:
// Calculate total billing amount per patient using items in billing.

// Query:

db.patients.aggregate([
  {
    $lookup: {
      from: "billing",
      localField: "patientId",
      foreignField: "patientId",
      as: "billingInfo"
    }
  },
  { $unwind: "$billingInfo" },
  { $unwind: "$billingInfo.items" },
  {
    $group: {
      _id: "$patientId",
      patientName: { $first: "$name" },
      totalAmount: { $sum: "$billingInfo.items.amount" }
    }
  },
  {
    $project: {
      _id: 0,
      patientId: "$_id",
      patientName: 1,
      totalAmount: 1
    }
  }
])
