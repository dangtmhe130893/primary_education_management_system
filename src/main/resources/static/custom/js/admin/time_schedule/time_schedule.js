$(document).ready(function () {

    let timeScheduleVue = new Vue({
        el: "#time_schedule",
        data: {
            listClass: [],
            className: "",
            classId: "",

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

            isEditing1: false,
            isEditing2: false,
            isEditing3: false,
            isEditing4: false,
            isEditing5: false,
            isEditing6: false,
            isEditing7: false,

            listSubjectUpdateRequest: [],
            dayOfWeekUpdateRequest: "",

        },
        watch: {
            className() {
                this.classId = Number($("#select-class option:selected").attr("id").replace("class_id_", ""));
                this.getTimeSchedule();
            }
        },
        methods: {
            getTimeSchedule(){
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/timeSchedule/getTimeSchedule/" + self.classId,
                    success: function (response) {
                        if(response.status.code === 1000) {
                            self.listTimeSchedule = response.data;

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
            loadListSubject() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/subject/list",
                    success: function (response) {
                        if(response.status.code === 1000) {
                            self.listSubject = response.data;
                        }
                    }
                })
            },
            resetListSubjectUpdate() {
                this.listSubjectUpdateRequest = [];
            }
        },
        mounted() {
            let self = this;
            self.loadListClass();
            self.loadListSubject();

            $(document).on("click", ".btn-end-col", function (){
                let isUpdate = !$(this).parent().find(".subject-item").prop('disabled');

                self.dayOfWeekUpdateRequest = $(this).attr("id").replace("btn-end-col-", "")

                $(this).parent().find(".subject-item").each(function (){
                    self.listSubjectUpdateRequest.push($(this).val());
                })

                switch (self.dayOfWeekUpdateRequest) {
                    case "MONDAY": {
                        self.isEditing1 = ! self.isEditing1;
                        break;
                    }
                    case "TUESDAY": {
                        self.isEditing2 = ! self.isEditing2;
                        break;
                    }
                    case "WEDNESDAY": {
                        self.isEditing3 = ! self.isEditing3;
                        break;
                    }
                    case "THURSDAY": {
                        self.isEditing4 = ! self.isEditing4;
                        break;
                    }
                    case "FRIDAY": {
                        self.isEditing5 = ! self.isEditing5;
                        break;
                    }
                    case "SATURDAY": {
                        self.isEditing6 = ! self.isEditing6;
                        break;
                    }
                    case "SUNDAY": {
                        self.isEditing7 = ! self.isEditing7;
                        break;
                    }
                }

                let data = {
                    classId: self.classId,
                    dayOfWeekUpdateRequest: self.dayOfWeekUpdateRequest,
                    listSubjectUpdateRequest: self.listSubjectUpdateRequest,
                }

                self.resetListSubjectUpdate();

                if(!isUpdate) {
                    return;
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

                        if(response.status.code === 1000) {
                            self.getTimeSchedule();
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else {
                            window.alert.show("error", "Lưu thất bại", 2000);
                        }
                    }
                })
            })
        }
    })
})