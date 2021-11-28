$(document).ready(function () {

    let timeScheduleTableVue = new Vue({
        el: "#time_schedule",
        data: {


            listClass: [],
            className: "",
            classId: "",

            homeroomTeacher: "",
            roomNameDefault: "",

            listSubject: [],

            listTimeSchedule: [],
            listTimeScheduleMonday: [],
            listTimeScheduleTuesday: [],
            listTimeScheduleWednesday: [],
            listTimeScheduleThursday: [],
            listTimeScheduleFriday: [],
            listTimeScheduleSaturday: [],
            listTimeScheduleSunday: [],

            isDisabledColMonday: true,
            isDisabledColTuesday: true,
            isDisabledColWednesday: true,
            isDisabledColThursday: true,
            isDisabledColFriday: true,
            isDisabledColSaturday: true,
            isDisabledColSunday: true,


        },
        watch: {
            className(value) {
                popupVue.nameClass = value;
                this.classId = Number($("#select-class option:selected").attr("id").replace("class_id_", ""));
                this.getTimeSchedule();
            },
        },
        methods: {
            loadListClass() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/class/getList",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listClass = response.data;
                        }
                    }
                })
            },
            getTimeSchedule() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/timeSchedule/getTimeSchedule/" + self.classId,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.roomNameDefault = data.roomNameDefault;
                            self.homeroomTeacher = data.homeroomTeacher;
                            self.listTimeSchedule = data.listTimeSchedule;

                            self.listTimeScheduleMonday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "MONDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleTuesday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "TUESDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleWednesday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "WEDNESDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleThursday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "THURSDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleFriday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "FRIDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleSaturday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "SATURDAY";
                            }).sort((frameTimeId1, frameTimeId2) => frameTimeId1 - frameTimeId2);

                            self.listTimeScheduleSunday = self.listTimeSchedule.filter(timeSchedule => {
                                return timeSchedule.dayOfWeek === "SUNDAY";
                            })
                        }
                    }
                })
            },

        },
        mounted() {
            let self = this;
            self.loadListClass();

            $(document).on("click", ".time-schedule-cell", function () {
                popupVue.timeScheduleId = Number($(this).attr('id').replace('time-schedule-', ''));
                $("#modal_add_time_schedule").modal("show");
                popupVue.detailCellTimeSchedule();
            })

        }
    })

    let popupVue = new Vue({
        el: "#modal_add_time_schedule",
        data: {
            isShowErrorSubject: false,
            isShowErrorTeacher: false,

            nameClass: "",
            dayOfWeek: "",
            nameFrameTime: "",

            timeScheduleId: "",

            listSubject: [],
            listTeacher: [],
            subjectId: null,
            teacherId: null,
            roomId: 0,
            listRoom: [],
        },
        watch: {
            subjectId(value) {
                if (value == null) {
                    return;
                }
                this.validateSubjectId();
                this.loadListTeacher(value);
            },
            teacherId(value) {
                if (value == null) {
                    return;
                }
                this.validateTeacherId();
            }
        },
        methods: {
            validateForm() {
                this.validateSubjectId();
                this.validateTeacherId();

                return !this.isShowErrorSubject && !this.isShowErrorTeacher;
            },
            validateSubjectId() {
                this.isShowErrorSubject = !this.subjectId;
            },
            validateTeacherId() {
                this.isShowErrorTeacher = !this.teacherId;
            },
            loadListSubject() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/subject/list",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listSubject = response.data;
                        }
                    }
                })
            },

            loadListRoom() {
                let self = this;
                $.ajax({
                    type: "GET",
                    url: "/api/room/getList",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listRoom = response.data;
                        }
                    }
                })
            },
            loadListTeacher(subjectId) {
                if (!subjectId) {
                    return;
                }
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/user/listTeacherForSubject/" + Number(subjectId),
                    success: function (response) {
                        self.listTeacher = response;
                    }
                })
            },
            detailCellTimeSchedule() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/timeSchedule/detail/" + self.timeScheduleId,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.nameFrameTime = data.nameFrameTime;
                            self.dayOfWeek = self.convertTextDayOfWeekToVietNamese(data.dayOfWeek);
                            self.subjectId = data.subjectId;
                            self.listTeacher = data.listTeacher;
                            self.teacherId = data.teacherId;
                        } else {
                            window.alert.show("error", "Lỗi", 2000);
                        }
                    }
                })
            },

            convertTextDayOfWeekToVietNamese(textEnglish) {
                switch (textEnglish) {
                    case "MONDAY": {
                        return "Thứ hai";
                        break;
                    }
                    case "TUESDAY": {
                        return "Thứ ba";
                        break;
                    }
                    case "WEDNESDAY": {
                        return "Thứ tư";
                        break;
                    }
                    case "THURSDAY": {
                        return "Thứ năm";
                        break;
                    }
                    case "FRIDAY": {
                        return "Thứ sáu";
                        break;
                    }
                    case "SATURDAY": {
                        return "Thứ bảy";
                        break;
                    }
                    case "SUNDAY": {
                        return "Chủ nhật";
                        break;
                    }
                }
            },

            saveTimeSchedule() {
                let self = this;

                if (!self.validateForm()) {
                    return;
                }

                let data = {
                    timeScheduleId: self.timeScheduleId,
                    subjectId: self.subjectId,
                    teacherId: self.teacherId,
                    roomId: self.roomId ? self.roomId : null,
                }

                $.ajax({
                    type: "POST",
                    url: "/api/timeSchedule/save",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        if (response.status.code === 1000) {
                            $("#modal_add_time_schedule").modal("hide");
                            timeScheduleTableVue.getTimeSchedule();
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else if (response.status.code === 1003){
                            window.alert.show("error", response.data + " đã có lịch dạy vào thời điểm này", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                        }
                    }
                })
            },
            resetPopup() {
                this.dayOfWeek = "";
                this.nameFrameTime = "";

                this.timeScheduleId = "";

                this.listTeacher = [];
                this.subjectId = null;
                this.teacherId = null;
                this.roomId = 0;

                this.isShowErrorSubject = false;
                this.isShowErrorTeacher = false;

            }
        },
        mounted() {
            let self = this;
            self.loadListSubject();
            self.loadListRoom();

            $('#modal_add_time_schedule').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

        }
    })
})