// Question 2: Add doctor info to prescriptions
// Goal:
// For each prescription, return:

// prescriptionId

// patientName

// doctorName

// medicineName

// dosage

// Query:

db.prescriptions.aggregate([
  {
    $lookup: {
      from: "patients",
      localField: "patientId",
      foreignField: "patientId",
      as: "patientInfo"
    }
  },
  {
    $lookup: {
      from: "staff",
      localField: "doctorId",
      foreignField: "staffId",
      as: "doctorInfo"
    }
  },
  { $unwind: "$patientInfo" },
  { $unwind: "$doctorInfo" },
  { $unwind: "$medicines" },
  {
    $project: {
      prescriptionId: 1,
      patientName: "$patientInfo.name",
      doctorName: "$doctorInfo.name",
      medicineName: "$medicines.name",
      dosage: "$medicines.dosage"
    }
  }
])