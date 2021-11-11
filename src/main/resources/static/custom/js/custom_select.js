var customSelect = (function(){
    var init = function(config){
        if(config.needConfirm == null || config.needConfirm == undefined){
            config.needConfirm = true;
        }
        var showingElements = [];
        var selectedElements = [];
        var waitingConfirmElements = [];
        var onChangeCallback = config.onChange;
        var onCloseSelect  = config.onCloseSelect;
        var onSelect = config.onSelect;
        var onRemove = config.onRemove;
        var inputIdName = config.element;
        var wrapperIdName = inputIdName +"_wrapper";
        var inputId = "#"+inputIdName;
        var wrapperId = "#"+ wrapperIdName;
        var numRunningRequest = 0;
        $(inputId).wrap("<div class='select-wrapper custom-select' id='"+wrapperIdName+"'></div>");
        var searchResult = 	'<div class="container-item clearfix hidden">\
									<div class="list-item"></div>\
									<div style="padding: 15px;height: 55px;position: relative;">\
										<span class="total-item-search"><span>該当データ： </span>\
										<span class="number-item">0</span> 件</span>\
										<a class="clear-all">すべて削除</a>\
										<span class="btn btn-sm btn-primary close-select-item">閉じる</span>\
									</div>\
								<div>';

        var selectedElementIdName = "selected_element_"+inputIdName;
        var selectedElementId = "#"+selectedElementIdName;
        var classes = $(inputId).attr("class");
        var style = $(inputId).attr("style");
        $(wrapperId).append("<span class='selected-element hidden "+classes+"' id='"+selectedElementIdName+"' style='"+style+"'></span>");
        $(wrapperId).append(searchResult);
        if(config.single){
            // $(wrapperId+" .close-select-item").addClass("hidden");
            $(wrapperId+" .clear-all").addClass("hidden");
        }

        var styleContainerItemElement = function(){
            var width = $(inputId).outerWidth();
            $(wrapperId+ " .container-item").width(width);
            var height = $(inputId).outerHeight();
            $(wrapperId + " .container-item").css("top",height+"px");
        };

        var validateRenderData = function(data){
            for(var i=0; i < data.length; i++){
                if(!data[i]["text"] || !data[i]["id"]){
                    console.error(" Element must have property id and text");
                    return false;
                }
            }
            return true;
        }

        var render = function(content){
            numRunningRequest --;
            if(numRunningRequest > 0){
                return;
            }
            var numElement = content.numberElement;
            showingElements = content.elements;
            if(validateRenderData(showingElements) === false){
                return;
            }
            $(wrapperId+" .number-item").html(numElement);
            styleContainerItemElement();
            $(wrapperId+" .container-item").removeClass("hidden");
            $(wrapperId+" .list-item").empty();
            for(var i=0; i < showingElements.length; i++){
                var inSelectedElement = selectedElements.findIndex(function(item){ return item.id == showingElements[i].id }) > -1;
                var checkbox = "";
                if(inSelectedElement){
                    checkbox = "<input class='showing-element' type='checkbox' checked='checked' data-id='"+showingElements[i].id+"'>";
                }else{
                    checkbox = "<input class='showing-element' type='checkbox' data-id='"+showingElements[i].id+"'>";
                }
                if(config.single){
                    $(wrapperId+" .list-item").append("<div class='item showing-element' data-id='"+showingElements[i].id+"'>"+showingElements[i].text+"</div>");
                }else{
                    $(wrapperId+" .list-item").append("<div class='item showing-element' data-id='"+showingElements[i].id+"'>"+showingElements[i].text+checkbox+"</div>");
                }
            }
        }

        if(config.defaultValue ){
            if(config.single){
                $(selectedElementId).removeClass("hidden");
                $(selectedElementId).html("<strong>"+config.defaultValue.text+"<i class='fa fa-remove remove-el-btn'></i> </strong>");
                $(inputId).addClass("hidden");
                $(wrapperId+" .container-item").addClass("hidden");
                selectedElements.push(config.defaultValue);
            }else{
                selectedElements = config.defaultValue;
            }

        }

        // show data when user focus input element with condition is input doesn't have value.
        if(config.showOnFocusData && config.showOnFocusData.length > 0){


            $(inputId).focus(function () {
                var value = $(inputId).val();
                if(value && value.length > 1){
                    return;
                }
                var content = {
                    elements: config.showOnFocusData,
                    numberElement : config.showOnFocusData.length
                };
                render(content);
            });
        }

        $(inputId).keyup(function(){
            numRunningRequest ++ ;
            var key = $(this).val();
            config.ajax(key,render);
        });
        $(inputId).click(function(){
            numRunningRequest ++ ;
            var key = $(this).val();
            config.ajax(key,render);
        });


        $(wrapperId).click(function(e){
            var $target = $(e.target);

            // listen user click showing element in drop down list
            if($target.hasClass("showing-element")){
                var id  = $target.data("id");
                var clickedElement = showingElements.find(function(el){ return el.id == id });
                if(config.single){
                    $(selectedElementId).removeClass("hidden");
                    $(selectedElementId).html("<strong>"+clickedElement.text+"<i class='fa fa-remove remove-el-btn'></i> </strong>");
                    $(inputId).addClass("hidden");
                    $(wrapperId+" .container-item").addClass("hidden");
                    selectedElements.push(clickedElement);
                    if(onSelect){
                        onSelect(clickedElement);
                    }
                }else{
                    var isSelectAction;
                    if(!$target.is("input")){
                        // toggle status of checkbox
                        var newStatus = !$target.find("input").prop("checked");
                        $target.find("input").prop("checked",newStatus);
                        isSelectAction = newStatus;
                    }else{
                        isSelectAction = $target.prop("checked");
                    }
                    if(isSelectAction){
                        if(config.needConfirm){
                            waitingConfirmElements.push(clickedElement);
                        }else{
                            selectedElements.push(clickedElement);
                        }
                        if(onSelect){
                            onSelect(clickedElement);
                        }
                    }else{
                        var index = selectedElements.findIndex(function(el){ return el.id == clickedElement.id; });
                        if(index > -1){
                            selectedElements.splice(index,1);
                        }
                        if(onRemove){
                            onRemove(clickedElement);
                        }
                        index = waitingConfirmElements.findIndex(function(el){ return el.id == clickedElement.id; });
                        if(index > -1){
                            waitingConfirmElements.splice(index,1);
                        }
                    }
                }
            }
            //listen user confirm to close drop down list and add to selected.
            if($target.hasClass("close-select-item")){
                $(wrapperId+" .container-item").addClass("hidden");
                selectedElements =  selectedElements.concat(waitingConfirmElements);
                if(onCloseSelect){
                    onCloseSelect(waitingConfirmElements,selectedElements);
                }
                waitingConfirmElements = [];
                e.stopPropagation();
            }

            // listen user click remove selected element in input
            if($target.hasClass("remove-el-btn")){
                $(selectedElementId).html("");
                $(inputId).removeClass("hidden");
                $(selectedElementId).addClass("hidden");
                $(inputId).val("");
                selectedElements = [];
                if(onRemove){
                    onRemove();
                }
            }

            if($target.hasClass("clear-all")){
                selectedElements = [];
                waitingConfirmElements = [];
                var showingEls = $(".showing-element");
                for(var i=0; i < showingEls.length; i++){
                    $(showingEls[i]).prop("checked",false);
                }

                if(config.onClearAll){
                    config.onClearAll();
                }

            }
        });

        function showSelectedElement(clickedElement) {
            $(selectedElementId).removeClass("hidden");
            $(selectedElementId).html("<strong>" + clickedElement.text + "<i class='fa fa-remove remove-el-btn'></i> </strong>");
            $(inputId).addClass("hidden");
        }
        return {
            hideSelectDropdown: function(){
                $(wrapperId+" .container-item").addClass("hidden");
                waitingConfirmElements = [];
            },
            getSelected: function(){
                return selectedElements;
            },
            setValues: function(elements){
                selectedElements = selectedElements.concat(elements);
                if(config.onSetValues){
                    config.onSetValues(elements);
                }
                if(config.single==true){
                    showSelectedElement(elements[0])
                }
            },
            onChange : function(callback){
                onChangeCallback = callback;
            },
            onSelect: function(callback){
                onSelect = callback;
            },
            onRemove: function(callback){
                onRemove = callback;
            },
            remove: function(elementId){
                var index = selectedElements.findIndex(function(el){ return el.id === elementId; });
                selectedElements.splice(index,1);
                var showingEl = $(".showing-element").find("[data-id='"+elementId+"']");
                if(showingEl){
                    $(showingEl).prop("checked",false);
                }
                if(config.single){
                    $(selectedElementId).html("");
                    $(inputId).removeClass("hidden");
                    $(selectedElementId).addClass("hidden");
                    $(inputId).val("");
                    selectedElements = [];
                }
                if(onRemove){
                    onRemove();
                }
            }
        };
    }

    return function (config) {
        return init(config);
    }

})();

// Ex:
// var emailInput = customSelect({
//	element: "email_input",
//	ajax: function(render){
//		var elements = [{id: 1, text: "huudatbk58@gmail.com"},{id : 2, text: "datnh220195@gmail.com"},{id: 3, text: "datnh@rabiloo.com"}];
//		render({
//			numberElement:  10,
//			elements: elements
//		});
//	},
//  onChange: function(selectedElement){
//
//  },
//  onCloseSelect: function(selecedElements){
//
//	},
//	single: true
// });
// emailInput.getSelected();
//



