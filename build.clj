(require 'cljs.build.api)

(cljs.build.api/build "src" {:output-to "resources/public/core.js"})
