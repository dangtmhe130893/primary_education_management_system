Vue.component("image_template", {
    props: ['prop'],
    template: "#image_template",
    data: function () {
        return {
            hasImage: false,
            image: null,
        };
    },
    mounted: function () {
        var self = this;
        var data = self.prop;
        if (data != null) {
            self.hasImage = data.hasImage;
            self.image =  data.image;
        }
        $(this.$el).click(function () {
            $(this).find("input").trigger("click");
        });
        $(".file-input").click(function (e) {
            e.stopPropagation();
        });
        $(this.$el).find(".file-input").change(function () {
            if (this.files.length === 0) {
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                $(this.$el).find(".file-input").val("");
                return;
            } else {
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.image = this.files[0];
                    self.prop.hasImage = true;
                    self.prop.image = this.files[0];
                    self.displayImage(this.files[0]);
                    self.replaceElementInput();
                    return;
                }
                $(this.$el).find(".file-input").val("");
                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG をアップロードしてください。", 3000);
                return;
            }
        });
    },
    methods: {
        replaceElementInput: function () {
            $(this.$el).find(".file-input").val("");
        },
        validateImageFile: function (file) {
            var validImageTypes = ["image/jpeg", "image/png"];

            if (validImageTypes.indexOf(file.type) > -1) {
                return true;
            } else {
                return false;
            }
        },
        removeImage: function () {
            this.image = null;
            this.hasImage = false;
            this.prop.image = null;
            this.prop.hasImage = false;
            $(this.$el).find(".file-input").val("")
        },
        displayImage: function (image) {
            var fileReader = new FileReader();
            var img = $(this.$el).find(".image-preview").get(0);
            fileReader.onload = function (e) {
                img.src = e.target.result;
            };
            fileReader.readAsDataURL(image);
            img.title = image.name;
        }
    }
});

Vue.component("image-upload-component-event", {
    props: ['prop'],
    template: "#image-uploader-event",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {
        var self = this;
        $(this.$el).click(function () {
            $(this).find("input").trigger("click");
        });

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function () {
            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);

                $(this.$el).find(".file-input").val("");
                return;
            } else {
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    // self.replaceElementInput();
                    return;
                }
                $(this.$el).find(".file-input").val("");
                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG をアップロードしてください。", 3000);
                return;
            }
        });
    }, // end mounted property
    methods: {
        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
        },
        replaceElementInput: function () {
            $(this.$el).find(".file-input").val("");
        },
        validateImageFile: function (file) {
            var validImageTypes = ["image/jpeg", "image/png"];

            if (validImageTypes.indexOf(file.type) > -1) {
                return true;
            } else {
                return false;
            }
        },
        removeFile: function () {
            this.hasFile = false;
            this.hasImage = false;
            $(this.$el).find(".file-input").val("")
        },
        removeImage: function () {

            this.hasImage = false;
            this.hasFile = false;
            $(this.$el).find(".file-input").val("")
        },
        displayImage: function (image) {
            var fileReader = new FileReader();
            var img = $(this.$el).find(".image-preview").get(0);

            fileReader.onload = function (e) {
                img.src = e.target.result;
            };
            fileReader.readAsDataURL(image);
            img.title = image.name;
        },
        getSelectedFile: function () {
            var files = $(this.$el).find(".file-input")[0].files;
            if (this.hasImage || this.hasFile) {
                return files[0];
            }

            return null;
        },
        clear: function () {
            this.hasImage = false;
            this.hasFile = false;
            $(this.$el).find(".file-input").val("")
        }
    }// end method property
});
