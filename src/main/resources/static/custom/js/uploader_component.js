Vue.component("file-upload-component", {
    template: "#file-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;

        $(this.$el).find(".upload-icon").click(function (e) {

            $(this).prev().trigger("click");
        });

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            $("#btn_submit_update_logo").removeAttr('disabled');
            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                $('input[name="image-menu"]').prop('checked', false);
                $('input[name="c-image-menu"]').prop('checked', false);
                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;

                    $(self.$el).find(".file-icon")[0].title = this.files[0].name;

                    return;
                }
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    return;
                }
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

Vue.component("image_logo_app", {
    template: "#file-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;

        $(this.$el).find(".upload-icon").click(function (e) {

            $(this).prev().trigger("click");
        });

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            $("#btn_submit_update_logo").removeAttr('disabled');
            if (this.files.length === 0) {
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                $('input[name="image-menu"]').prop('checked', false);
                $('input[name="c-image-menu"]').prop('checked', false);
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    return;
                }
                $("#btn_submit_update_logo").prop('disabled', true);
                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG をアップロードしてください。", 3000);
                return;
            }
        });
    }, // end mounted property
    methods: {
        validateImageFile: function (file) {
            var validImageTypes = ["image/jpeg", "image/png"];
            if (validImageTypes.indexOf(file.type) > -1) {
                return true;
            } else {
                return false;
            }
        },
        removeFile: function () {

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


Vue.component("common-file-upload-component", {
    template: "#file-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;

        $(this.$el).children().click(function () {
            $(this).children().first().trigger("click")
        })
//				$(this.$el).find(".upload-icon").click(function(e){
//					
//					$(this).prev().trigger("click");
//				});

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {

            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {

                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;

                    $(self.$el).find(".file-icon")[0].title = this.files[0].name;

                    $("#catalog_link").attr('disabled', 'disabled')
                    return;
                }
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    $("#catalog_link").attr('disabled', 'disabled')
                    return;
                }

                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG, .PDFをアップロードしてください。", 3000);

                return;
            }
        });
    }, // end mounted property
    methods: {

        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
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
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
            $(this.$el).find(".file-input").val("")
        },
        removeImage: function () {
            this.hasImage = false;
            this.hasFile = false;
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
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
            $("#catalog_link_updated").attr('disabled', 'disabled');
            $("#catalog_link_updated").attr('value', '');
        },
        getSelectedFile: function () {
            var files = $(this.$el).find(".file-input")[0].files;
            if (this.hasImage || this.hasFile) {
                return files[0];
                $("#catalog_link_updated").attr('disabled', 'disabled');
                $("#catalog_link_updated").attr('value', '');
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

Vue.component("icon-upload-component", {
    template: "#icon-uploader",
    data: function () {
        return {
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;
        $(".new-upload-icon .file-input").on('change', function () {
            var typeFile = $(this).prop('files')[0].type;
            if (typeFile.indexOf("image") !== -1) {
                if (!$("#required_image").hasClass("hidden")) {
                    $("#required_image").addClass("hidden");
                }
            }

        })
        $(".detail-upload-icon .file-input").on('change', function () {
            var typeFile = $(this).prop('files')[0].type;
            if (typeFile.indexOf("image") !== -1) {
                if (!$("#required_image_updated").hasClass("hidden")) {
                    $("#required_image_updated").addClass("hidden");
                }
            }

        })
        $(this.$el).find(".upload-icon").click(function (e) {

            $(this).prev().trigger("click");
        });

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                $('input[name="image-menu"]').prop('checked', false);
                $('input[name="c-image-menu"]').prop('checked', false);
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    return;
                }
                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG をアップロードしてください。", 3000);
                return;
            }
        });
    }, // end mounted property
    methods: {
        validateImageFile: function (file) {
            var validImageTypes = ["image/jpeg", "image/png"];
            if (validImageTypes.indexOf(file.type) > -1) {
                return true;
            } else {
                return false;
            }
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

Vue.component("image-upload-component", {
    props: ['prop'],
    template: "#image-uploader",
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


Vue.component("starting-image-upload-component", {
    template: "#starting-image-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;
        /*$(this.$el).find(".image").click(function(e){
            $(this).find("input").trigger("click");
        });*/


        $(self.$el).find(".introduction-icon").click(function (e) {
            $(self.$el).find("input").trigger("click");
        });


        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;
                    $(self.$el).find(".file-icon")[0].title = this.files[0].name;
                    return;
                }
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    return;
                }
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

Vue.component("introducing-upload-component", {
    template: "#introducing-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;

        $(self.$el).find(".introduction-icon").click(function (e) {
            $(self.$el).find("input").trigger("click");
        });

        /*$(this.$el).click(function(e){
            $(this).find("input").trigger("click");
        });*/

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;
                    $(self.$el).find(".file-icon")[0].title = this.files[0].name;
                    return;
                }
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    return;
                }
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

Vue.component("introducing-company-component", {
    template: "#introducing-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

    }, // end mounted property
    methods: {

        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
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

Vue.component("common-pdf-upload-component", {
    template: "#file-uploader",
    data: function () {
        return {
            hasFile: false,
            hasImage: false,
            fileName: null
        };
    },
    mounted: function () {

        var self = this;

        $(this.$el).find(".upload-icon").click(function (e) {

            $(this).prev().trigger("click");
        });

        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {

            if (this.files.length === 0) {
                self.hasFile = false;
                self.hasImage = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {

                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;

                    $(self.$el).find(".file-icon")[0].title = this.files[0].name;

                    $("#catalog_link").attr('disabled', 'disabled')
                    return;
                }
                if (self.validateImageFile(this.files[0])) {
                    self.hasImage = true;
                    self.displayImage(this.files[0]);
                    $("#catalog_link").attr('disabled', 'disabled')
                    return;
                }

                window.alert.show("error", "ファイル拡張が正しくないです。.JPG, .PNG, .PDFをアップロードしてください。", 3000);

                return;
            }
        });
    }, // end mounted property
    methods: {

        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
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
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
            $(this.$el).find(".file-input").val("")
        },
        removeImage: function () {
            this.hasImage = false;
            this.hasFile = false;
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
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
            $("#catalog_link_updated").attr('disabled', 'disabled');
            $("#catalog_link_updated").attr('value', '');
        },
        getSelectedFile: function () {
            var files = $(this.$el).find(".file-input")[0].files;
            if (this.hasImage || this.hasFile) {
                return files[0];
                $("#catalog_link_updated").attr('disabled', 'disabled');
                $("#catalog_link_updated").attr('value', '');
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


Vue.component("new-common-pdf-upload-component", {
    template: "#pdf_uploader",
    data: function () {
        return {
            hasFile: false,
            fileName: null
        };
    },
    mounted: function () {
        var self = this;
        $(".pdf-uploader .file-input").on('change', function () {
            if ($(this).prop('files')[0].type === "application/pdf") {
                if (!$("#required_pdf_file").hasClass("hidden")) {
                    $("#required_pdf_file").addClass("hidden");
                }
            }
        });
        $(".pdf-uploader").on('click', function () {
            $(".pdf-uploader .file-input").trigger("click");
        })
        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            if (this.files.length === 0) {
                self.hasFile = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;
                    $(self.$el).find(".pdf-icon")[0].title = this.files[0].name;
                    $(self.$el).find("#name_file")[0].innerHTML = this.files[0].name;
                    return;
                }
                window.alert.show("error", "ファイル拡張が正しくないです。.PDFをアップロードしてください。", 3000);
                return;
            }
        });
    }, // end mounted property
    methods: {
        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
        },
        removeFile: function () {
            this.hasFile = false;
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
            $(this.$el).find(".file-input").val("")
            $(this.$el).find("#name_file")[0].innerHTML = "";
        },
        displayImage: function (image) {
            var fileReader = new FileReader();
            var img = $(this.$el).find(".image-preview").get(0);
            fileReader.onload = function (e) {
                img.src = e.target.result;
            };
            fileReader.readAsDataURL(image);
            img.title = image.name;
            $("#catalog_link_updated").attr('disabled', 'disabled');
            $("#catalog_link_updated").attr('value', '');
        },
        getSelectedFile: function () {
            var files = $(this.$el).find(".file-input")[0].files;
            if (this.hasFile) {
                return files[0];
                $("#catalog_link_updated").attr('disabled', 'disabled');
                $("#catalog_link_updated").attr('value', '');
            }
            return null;
        },
        clear: function () {
            this.hasFile = false;
            $(this.$el).find(".file-input").val("")
        }
    }// end method property
});

Vue.component("detail-common-pdf-upload-component", {
    template: "#detail_pdf_uploader",
    data: function () {
        return {
            hasFile: false,
            fileName: null
        };
    },
    mounted: function () {
        var self = this;
        $(".detail-pdf-uploader .file-input").on('change', function () {
            if ($(this).prop('files')[0].type === "application/pdf") {
                if (!$("#required_pdf_file_updated").hasClass("hidden")) {
                    $("#required_pdf_file_updated").addClass("hidden");
                }
            }
        });
        $(".detail-pdf-uploader").on('click', function () {
            $(".detail-pdf-uploader .file-input").trigger("click");
        })
        $(".file-input").click(function (e) {
            e.stopPropagation();
        });

        $(this.$el).find(".file-input").change(function (e) {
            if (this.files.length === 0) {
                self.hasFile = false;
            } else if (this.files[0].size > fileSize3MB) {
                window.alert.show("error", "ファイルの最大サイズは３MBです。", 3000);
                return;
            } else {
                if (self.validatePdfFile(this.files[0])) {
                    self.hasFile = true;
                    $(self.$el).find(".pdf-icon")[0].title = this.files[0].name;
                    $(self.$el).find("#name_file")[0].innerHTML = this.files[0].name;
                    return;
                }
                window.alert.show("error", "ファイル拡張が正しくないです。.PDFをアップロードしてください。", 3000);
                return;
            }
        });
    }, // end mounted property
    methods: {
        validatePdfFile: function (file) {
            var pdfType = "application/pdf";
            return file.type === pdfType;
        },
        removeFile: function () {
            this.hasFile = false;
            $("#catalog_link_updated").removeAttr('disabled');
            $("#catalog_link_updated").attr('value', $("#catalog_link").val());
            $(this.$el).find(".file-input").val("")
            $(this.$el).find("#name_file")[0].innerHTML = "";
        },
        displayImage: function (image) {
            var fileReader = new FileReader();
            var img = $(this.$el).find(".image-preview").get(0);
            fileReader.onload = function (e) {
                img.src = e.target.result;
            };
            fileReader.readAsDataURL(image);
            img.title = image.name;
            $("#catalog_link_updated").attr('disabled', 'disabled');
            $("#catalog_link_updated").attr('value', '');
        },
        getSelectedFile: function () {
            var files = $(this.$el).find(".file-input")[0].files;
            if (this.hasFile) {
                return files[0];
                $("#catalog_link_updated").attr('disabled', 'disabled');
                $("#catalog_link_updated").attr('value', '');
            }
            return null;
        },
        clear: function () {
            this.hasFile = false;
            $(this.$el).find(".file-input").val("")
        }
    }// end method property
});

