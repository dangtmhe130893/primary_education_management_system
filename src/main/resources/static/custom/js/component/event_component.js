Vue.component("miles_stone", {
    props: ['prop'],
    template: "#miles_stone_component",
    data: function () {
        return {
            isValid: true,
            isActiveEvent: false
        }
    },
    mounted: function () {
        this.resetForm();
        this.isvalid = this.prop.isValid;
        this.isActiveEvent = this.prop.isActiveEvent;
    },
    methods: {
        validateMilesStone: function () {
            this.validate();
            this.passDataToParent();
        },
        resetForm: function () {
            this.isValid = true;
            this.isActiveEvent = false;
        },
        validate: function () {
            var milesStone = this.prop.milesStone;
            //validate one
            if (milesStone.start > 0 && milesStone.end > 0) {
                if (milesStone.position === 1) {
                    if (milesStone.start !== 1) {
                        this.isValid = false;
                        return;
                    }
                }
                if (milesStone.start > milesStone.end) {
                    this.isValid = false;
                    return;
                } else {
                    this.isValid = true;
                }
            } else {
                this.isValid = false;
                return;
            }
            //valid all mile stone
            var listMileStone = editEvent.eventVue.event.milesStoneEntities;
            var previousMilesStone = getMilesStoneByPosition(listMileStone, milesStone.position - 1);
            var nextMilesStone = getMilesStoneByPosition(listMileStone, milesStone.position + 1);

            if (previousMilesStone != null) {
                if ((parseInt(previousMilesStone.end) + 1) > milesStone.start || (parseInt(previousMilesStone.end) + 1) < milesStone.start) {
                    this.isValid = false;
                    return;
                }
            }
            if (nextMilesStone != null) {
                if ((parseInt(milesStone.end) + 1) > nextMilesStone.start || (parseInt(milesStone.end) + 1) < nextMilesStone.start) {
                    this.isValid = false;
                    return;
                }
            }
            this.isValid = true;
        },
        passDataToParent: function () {
            this.prop.isValid = this.isValid;
        }


    }
});

var getMilesStoneByPosition = function (listMileStone, position) {
    var result = null;
    listMileStone.forEach(function (object) {
        if (object.milesStone.position === position) {
            result = object.milesStone;
            return false;
        }
    });
    return result;
};
Vue.component("reward_apply_component", {
    props: ['prop'],
    template: "#reward_apply_component",
    data: function () {
        return {
            code: null,
            id: null
        }
    },
    mounted: function () {
        this.code = this.prop.reward.applyStampEntity.code;
        this.id = this.prop.reward.id;
    },
    methods: {
        updateApplyStampComponent: function () {
            if (this.code == null || this.code.trim() === "") {
                window.alert.show("error", "応募コードを入力してください。", 1200);
            } else {
                editEvent.eventVue.updateApplyReward(this.id, this.code);
            }
        }
    }
});



