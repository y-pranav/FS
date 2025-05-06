const hospitalSchema = {
  staff: {
    _id: "ObjectId",
    staffId: "String", // Unique ID (e.g., D001, N001)
    name: "String",
    contactInfo: {
      phone: "String",
      email: "String"
    },
    joiningDate: "Date",
    department: ["String"],
    role: "Doctor | Nurse | Technician | Pharmacist | Admin",
    specializations: ["String"], // for Doctors
    consultationHours: [{ day: "String", start: "String", end: "String" }], // for Doctors
    assignedWardICU: "String", // for Nurses
    supervisorId: "String | null"
  },

  patients: {
    _id: "ObjectId",
    patientId: "String",
    name: "String",
    age: "Number",
    gender: "Male | Female | Other",
    address: "String",
    contact: "String",
    patientType: "Inpatient | Outpatient",
    insurance: {
      company: "String",
      policyNumber: "String",
      coverage: "Full | Partial | None"
    }
  },

  medicines: {
    _id: "ObjectId",
    medicineId: "String",
    name: "String",
    dosageForm: "String",
    stockLevel: "Number",
    reorderThreshold: "Number",
    supplierName: "String"
  },

  appointments: {
    _id: "ObjectId",
    appointmentId: "String",
    patientId: "String",
    doctorId: "String",
    appointmentDate: "Date",
    appointmentTime: "String",
    status: "Booked | Completed | Canceled"
  },

  prescriptions: {
    _id: "ObjectId",
    prescriptionId: "String",
    appointmentId: "String",
    patientId: "String",
    doctorId: "String",
    medicines: [{
      medicineId: "String",
      name: "String",
      dosage: "String",
      duration: "String"
    }],
    date: "Date"
  },

  lab_tests: {
    _id: "ObjectId",
    testId: "String",
    type: "String",
    patientId: "String",
    technicianId: "String",
    description: "String",
    cost: "Number",
    date: "Date"
  },

  billing: {
    _id: "ObjectId",
    billId: "String",
    patientId: "String",
    items: [{
      description: "String",
      amount: "Number"
    }],
    totalAmount: "Number",
    insuranceCovered: "Boolean",
    insuranceDetails: {
      company: "String",
      policyNumber: "String",
      coverage: "Full | Partial | None"
    },
    billingDate: "Date"
  }
};
