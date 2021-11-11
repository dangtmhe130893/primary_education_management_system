/**
 * 
 */

Vue.component("loader-component",{
	template: "<div class='loader-container' v-bind:class='{ hidden : !loading}'>\
				 <div class='loader'></div>\
			   </div>",
	data: function(){
		return {
			loading: false
		}
	},
	methods:{
		show: function(){
			this.loading = true;
		},
		hide: function(){
			this.loading = false;
		}
	}
});

Vue.component("modal-create-app-component",{
	template: '<div class="modal fade" id="require_create_app" tabindex="-1" role="dialog">'
		+ '<div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header">'
		+ ' <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
        + '<h2 class="modal-title">Warning</h2></div><div class="modal-body"><h4> Please create app information first! </h4> </div>'
     +  '<div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div></div></div></div>'
	,
	data: function(){
		return {
			loading: false
		}
	},
	methods:{
		show: function(){
			$('#require_create_app').modal('show') 
		},
		hide: function(){
			this.loading = false;
		}
	}
})

