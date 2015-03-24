/*
 * jQuery Multi Download  v3.0.3 
 * https://github.com/biesiad/multiDownload
 *
 * Copyright (c) 2011 Grzegorz Biesiadecki <gbiesiadecki@gmail.com>
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

(function ($) {

    var methods = {
        _download: function (options) {
            var triggerDelay = (options && options.delay) || 100;
            var cleaningDelay = (options && options.cleaningDelay) || 10000;

            this.each(function (index, item) {
                methods._createIFrame(item, index * triggerDelay, cleaningDelay);
            });
            return this;
        },

        _createIFrame: function (item, triggerDelay, cleaningDelay) {
            setTimeout(function () {
                var frame = $('<iframe style="display: none;" class="multi-download-frame"></iframe>');
                frame.attr('src', item.href || item.src );
                $("body").append(frame);
                setTimeout(function () { frame.remove(); }, cleaningDelay);
            }, triggerDelay);
        }
    };

    $.fn.multiDownload = function(options) {
        return methods._download.apply(this, arguments);
    };

})(jQuery);