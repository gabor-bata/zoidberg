/*      ___
 * (V) (;,;) (V) - Why not Zoidberg?
 */
(function() {
  var $location = $(location);
  var $window = $(window);
  var $exampleSelector = $("#input-code-example-select");

  function hashChangeEventHandler() {
    var hash = $location.attr('hash');
    if (hash) {
      var decompressed = LZString.decompressFromEncodedURIComponent(hash.substring(1));
      if (decompressed) {
        inputEditor.getDoc().setValue(decompressed);
      }
      $exampleSelector.val("");
    } else {
      $exampleSelector.val("/examples/weather_forecast.rb");
    }
    $exampleSelector.trigger("change");
  }

  var inputEditor = CodeMirror.fromTextArea(document.getElementById("input-code"), {
    mode: "text/x-ruby",
    lineNumbers: true,
    theme: "ambiance",
    matchBrackets: true,
    indentUnit: 2
  });

  var resultEditor = CodeMirror.fromTextArea(document.getElementById("result-code"), {
    mode: "application/x-json",
    lineNumbers: true,
    theme: "ambiance",
    matchBrackets: true,
    indentUnit: 2,
    readOnly: true
  });

  $("#send-button").on("click", function() {
    $.blockUI();
    $.ajax({
      url: "/weather",
      type: "POST",
      data: { code: inputEditor.getValue() }
    }).done(function(data) {
      resultEditor.getDoc().setValue(JSON.stringify(data["result"], null, 2));
    }).fail(function() {
      resultEditor.getDoc().setValue("Failed to send ajax request.");
    }).always(function() {
      $.unblockUI();
    });
  });

  $exampleSelector.on("change", function() {
    selectedScript = $(this).val();
    if (selectedScript) {
      $.blockUI();
      $.ajax({
        url: selectedScript,
        mimeType: "text/x-ruby"
      }).done(function(data) {
        inputEditor.getDoc().setValue(data);
      }).fail(function() {
        resultEditor.getDoc().setValue("Failed to load example.");
      }).always(function() {
        $.unblockUI();
      });
    }
  });

  $("#permalink-button").on("click", function() {
    var href = $location.attr('href').replace(/#.*/g, '');;
    resultEditor.getDoc().setValue(href + '#' + LZString.compressToEncodedURIComponent(inputEditor.getValue()));
  });

  $window.on('hashchange', hashChangeEventHandler);
  $window.trigger('hashchange');
})();
