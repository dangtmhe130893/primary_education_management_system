let materialVue;
let file;
$(document).ready(function () {

    materialVue = new Vue({
        el: "#modal_add_material",
        data: {
            id: "",
            grade: "Khối 1",
            type: "Bài giảng",
            name: '',
            subjectId: '',
            isUpdate: false,
            isShowBtnUploadFile: true
        },
        watch: {
            id(value) {
                this.isUpdate = !!value;
            },
        },
        methods: {
            saveMaterial() {
                let vm = this;
                let formData = new FormData();
                formData.append("id", vm.id);
                formData.append("grade", vm.grade);
                formData.append("name", vm.name);
                formData.append("subjectId", vm.subjectId);
                formData.append("type", vm.type);
                formData.append("id", vm.id);
                formData.append("content", CKEDITOR.instances['content'].getData());
                formData.append("file", file);

                $.ajax({
                    type: "POST",
                    url: "/api/material/save",
                    data: formData,
                    contentType: false,
                    processData: false,
                    dataType: "json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        if (response.status.code === 1000) {
                            listMaterialTable.ajax.reload();
                            $('#modal_add_material').modal("hide");
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                        }
                    }
                })

            }
        },
        mounted() {

            let self = this;

            $('#modal_add_material').on('hidden.bs.modal', function () {
                // self.resetPopup();
            })

            $("#form-pupil-account").validate({
                errorElement: "p",
                errorClass: "error-message",
                errorPlacement: function (error, element) {
                    error.insertBefore(element);
                },
                ignore: [],
                rules: {
                    name: {
                        required: true,
                        maxlength: 200,
                    },
                }

            });
        }
    })


    CKEDITOR.replace('content', {
        language: 'vi',
        height: 200,
        removePlugins: 'elementspath'
    });
    CKEDITOR.config.toolbar = [
        ['Styles', 'Format'],
        ['Bold', 'Italic', 'Underline', 'StrikeThrough', '-', 'Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'Find', 'Replace', '-', 'Outdent', 'Indent', '-', 'Print'],
        ['NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
        ['Image', 'Table', '-', 'Link', 'Flash', 'Smiley', 'TextColor', 'BGColor', 'Source']
    ];

    $(document).on('click', "#btn_upload_file", function () {
        $("#upload_file_input").click();
    })

    $('input[name="upload_file_input"]').change(function (e) {
        let fileName = e.target.files[0].name;
        file = e.target.files[0];
        let html = "<label class='selected-file'>" +
            "	<i class='fa fa-paperclip' style='margin-right: 5px'></i>" + fileName +
            "<i class='btn fa fa-remove btn-remove-file' id='" + fileName + "'></i>" +
            "</label>"
        $("#container_selected_file").append(html);
        materialVue.isShowBtnUploadFile = false;
    });

    $(document).on('click', '.btn-remove-file', function () {
        file = null;
        $("#upload_file_input").val('');
        $(this).parent().remove();
        materialVue.isShowBtnUploadFile = true;
    })

})