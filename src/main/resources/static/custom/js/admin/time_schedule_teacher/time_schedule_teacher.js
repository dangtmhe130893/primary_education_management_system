$(document).ready(function () {

    let timeScheduleTableVue = new Vue({
        el: "#frame_teach_class",
        data: {

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
                    url: "/api/timeSchedule/getTimeScheduleForTeacher/",
                    success: function (response) {
                        if (response.status.code === 1000) {
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

                            self.loadFrame("monday", self.listTimeScheduleMonday);
                            self.loadFrame("tuesday", self.listTimeScheduleTuesday);
                            self.loadFrame("wednesday", self.listTimeScheduleWednesday);
                            self.loadFrame("thursday", self.listTimeScheduleThursday);
                            self.loadFrame("friday", self.listTimeScheduleFriday);
                            self.loadFrame("saturday", self.listTimeScheduleSaturday);
                            self.loadFrame("sunday", self.listTimeScheduleSunday);


                        }
                    }
                })
            },
            loadFrame(day, listTimeSchedule) {
                $(".frame-" + day).each(function () {
                    let frameId = this.id.replace("frame_" + day + "_", "");
                    let isHasClass = false;
                    listTimeSchedule.forEach(function (time) {
                        if (time.frameTimeId == frameId) {
                            isHasClass = true;
                            $("#frame_" + day + "_" + frameId).append(`<div class="time-schedule-teacher-cell">
                                                                                <div>${time.nameSubject}</div>
                                                                                <div>${time.className} - ${time.roomName}</div>
                                                                            </div>`)
                        }
                    })
                    if (!isHasClass) {
                        $("#frame_" + day + "_" + frameId).append('<div class="time-schedule-teacher-cell"> - </div')
                    }
                })
            }

        },
        mounted() {
            let self = this;
            self.getTimeSchedule()
        }
    })

})