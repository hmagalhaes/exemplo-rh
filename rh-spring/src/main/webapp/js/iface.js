window.iface = {
	setupLang: function(cfg) {
		var form = cfg.el.parentElement;
		cfg.el.onchange = function() {
			var req = window.XMLHttpRequest ? new XMLHttpRequest() : 
					new ActiveXObject("Microsoft.XMLHTTP");

			req.onreadystatechange = function() {
				if (req.readyState == 4) {
					if (req.status == 200) {
						var r = null;
						try {
							r = eval(req.responseText);
							if (r.ok == true) {
								location.reload();
								return;  ////
							} else {
								alert(r.msg);
							}
						} catch (ex) {
							alert(cfg.error);
						}
					} else {
						alert(cfg.error);
					}
					form.reset();
				}
			};

			req.open("post", cfg.url);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("locale=" + cfg.el.value);
		};
	}
};