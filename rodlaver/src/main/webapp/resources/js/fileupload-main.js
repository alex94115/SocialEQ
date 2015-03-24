/*
 * jQuery File Upload Plugin JS Example 8.9.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#listingForm').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: 'fileUpload',
        filesContainer: $('#selectedFiles'),
        pasteZone: null,
        autoUpload: true,
        uploadTemplateId: null,
        downloadTemplateId: null,
        uploadTemplate: function (o) {
            var rows = $();
            $.each(o.files, function (index, file) {
                var row = $('<tr class="template-upload fade">' +
		                    '  <td>' +
		                    '    <span class="preview"></span>' +
		                    '  </td>' +
		                    '  <td>' +
		                    '    <p class="name"></p>' +
		                    '    <strong class="error text-danger"></strong>' +
		                    '  </td>' +
		                    '  <td>' +
		                    '    <p class="size">Processing...</p>' +
		                    '    <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>' +
		                    '  </td>' +
		                    '  <td>' +
		                    (!index && !o.options.autoUpload ? '<button class="btn btn-primary start" disabled><i class="glyphicon glyphicon-upload"></i><span>Start</span></button>' : '') +
		                    (!index ? '<button class="btn btn-warning cancel"><i class="glyphicon glyphicon-ban-circle"></i><span>Cancel</span></button>' : '') +
		                    '  </td>' +
                    		'</tr>');
                row.find('.name').text(file.name);
                row.find('.size').text(o.formatFileSize(file.size));
                if (file.error) {
                    row.find('.error').text(file.error);
                }
                rows = rows.add(row);
            });
            return rows;
        },
        downloadTemplate: function (o) {
            var rows = $();
            $.each(o.files, function (index, file) {
                var row = $('<tr class="template-download fade">' +
                            '  <td><span class="preview"></span></td>' +
                            '  <td>' +
                            '    <p class="name"></p>' + (file.error ? '<div class="error"></div>' : '') +
                            '  </td>' +
                            '  <td><span class="size"></span></td>' +
                            '  <td>' +
                            '    <button class="btn btn-danger delete">' +
                            '      <i class="glyphicon glyphicon-trash"></i>' +
                            '      <span>Delete</span>' +
                            '    </button>' +
                            '    <input type="checkbox" name="delete" value="1" class="toggle">' +
                            '  </td>' +
                            '</tr>');
                row.find('.size').text(o.formatFileSize(file.size));
                row.find('.delete').attr('data-type', file.deleteType );
                row.find('.delete').attr('data-url', file.deleteUrl );
                
                if (file.error) {
                    row.find('.name').text(file.name);
                    row.find('.error').text(file.error);
                } else {
                    row.find('.name').append($('<a></a>').text(file.name));
                    if (file.thumbnailUrl) {
                        row.find('.preview').append(
                            $('<a></a>').append(
                                $('<img>').prop('src', file.thumbnailUrl)
                            )
                        );
                    }
                    row.find('a')
                        .attr('data-gallery', '')
                        .prop('href', file.url);
                    row.find('.delete button')
                        .attr('data-type', file.delete_type)
                        .attr('data-url', file.delete_url);
                }
                rows = rows.add(row);
            });
            return rows;
        }
    });

    // Enable iframe cross-domain access via redirect option:
    $('#listingForm').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (window.location.hostname === "localhost" || window.location.hostname === "rodlaver.elasticbeanstalk.com" || window.location.hostname === "www.socialeq.co"  ) {
        // Initialize the form:
        $('#listingForm').fileupload('option', {
            url: 'uploadFiles',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 1000000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png|mp3|m4a|mov|3gp)$/i
        });
       
    } else {
        // Load existing files:
        $('#listingForm').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#listingForm').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#listingForm')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
    }

});