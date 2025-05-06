Hospital and Research Management
MongoDB Schema
1. Staff Collection
{
 _id: ObjectId,
 staffId: String, // Unique ID (e.g., D001, N001)
 name: String,
 contactInfo: {
 phone: String,
 email: String
 },
 joiningDate: Date,
 department: [String],
 role: {enum: ["Doctor", "Nurse", "Technician", "Pharmacist", "Admin"]},
 specializations: [String], // for Doctors
 consultationHours: [{day: String, start: String, end: String}], // for Doctors
 assignedWardICU: String, // for Nurses
 supervisorId: {type: String, ref: 'staff', default: null}
}
2. Patients Collection
{
 _id: ObjectId,
 patientId: String,
 name: String,
 age: Number,
 gender: {enum: ["Male", "Female", "Other"]},
 address: String,
 contact: String,
 patientType: {enum: ["Inpatient", "Outpatient"]},
 insurance: {
 company: String,
 policyNumber: String,
 coverage: {enum: ["Full", "Partial", "None"]}
 }
}
3. Medicines Collection
{
 _id: ObjectId,
 medicineId: String,
 name: String,
 dosageForm: String,
 stockLevel: Number,
 reorderThreshold: Number,
 supplierName: String
}
4. Appointments Collection
{
 _id: ObjectId,
 appointmentId: String,
 patientId: {type: String, ref: 'patients'},
 doctorId: {type: String, ref: 'staff'},
 appointmentDate: Date,
 appointmentTime: String,
 status: {enum: ["Booked", "Completed", "Canceled"]}
}
5. Prescriptions Collection
{
 _id: ObjectId,
 prescriptionId: String,
 appointmentId: {type: String, ref: 'appointments'},
 patientId: {type: String, ref: 'patients'},
 doctorId: {type: String, ref: 'staff'},
 medicines: [{
 medicineId: {type: String, ref: 'medicines'},
 name: String,
 dosage: String,
 duration: String
 }],
 date: Date
}
6. Lab Tests Collection
{
 _id: ObjectId,
 testId: String,
 type: String,
 patientId: {type: String, ref: 'patients'},
 technicianId: {type: String, ref: 'staff'},
 description: String,
 cost: Number,
 date: Date
}
7. Billing Collection
{
 _id: ObjectId,
 billId: String,
 patientId: {type: String, ref: 'patients'},
 items: [{
 description: String,
 amount: Number
 }],
 totalAmount: Number,
 insuranceCovered: Boolean,
 insuranceDetails: {
 company: String,
 policyNumber: String,
 coverage: {enum: ["Full", "Partial", "None"]}
 },
 billingDate: Date
}