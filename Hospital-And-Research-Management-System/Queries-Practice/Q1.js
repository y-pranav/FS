// Question 1: Join prescriptions with patients and medicines
// Goal:
// For each medicine in each prescription, show:

// prescriptionId

// patientName

// medicineName

// dosage

// Query:

db.prescriptions.aggregate([
  {
    $lookup: {
      from: "patients",
      localField: "patientId",
      foreignField: "patientId",
      as: "patient"
    }
  },
  { $unwind: "$patient" },
  { $unwind: "$medicines" },
  {
    $project: {
      _id: 0,
      prescriptionId: 1,
      patientName: "$patient.name",
      medicineName: "$medicines.name",
      dosage: "$medicines.dosage"
    }
  }
])