$(document).ready(function () {

    let timeScheduleVue = new Vue({
        el: "#time_schedule",
        data: {
            listClass: [],
            className: "",
            classId: "",

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

            textBtnColMonday: "Sửa",

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
            }
        },
        mounted() {
            let self = this;
            self.loadListClass();
        }
    })
})