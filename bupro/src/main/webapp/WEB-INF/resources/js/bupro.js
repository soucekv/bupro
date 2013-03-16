var client = {
	comment : function(_type, _id, _title, _text) {
		$.ajax({
			type: 'post',
			url: '/comment',
			data: {type : _type, id : _id, title: _title, text: _text},
			success: function(data) {
				var c = $.parseJSON(data);
				$("#comments:last").append('\
					<div class="comment">\
						<div>\
							<span>' + (c.author == null ? '' : c.author) + '</span>\
							<span>' + c.title + '</span>\
							</div>\
						<div>' + c.text + '</div>\
					</div>\
				');
			},
		});
	}
};