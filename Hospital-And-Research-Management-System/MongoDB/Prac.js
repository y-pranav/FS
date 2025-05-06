db.lab_tests.aggregate([
    {
        $lookup: {
            from: "patients",
            localField: "patientId",
            foreignField: "patientId",
            as: "patientInfo"
        }
    },
    {
        $project: {
            _id: 0,
            patientName: "$patientInfo.name",
            cost: "$cost",
            costCategory: {

            }
        }
    }
])