$(document).ready(function () {

    let timeScheduleTableVue = new Vue({
        el: "#time_schedule",
        data: {
            className: "",
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

        },
        methods: {
            getTimeSchedule() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/pupil/time_table",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.roomNameDefault = data.roomNameDefault;
                            self.homeroomTeacher = data.homeroomTeacher;
                            self.listTimeSchedule = data.listTimeSchedule;
                            self.className = data.className;

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
            self.getTimeSchedule()
        }
    })

})