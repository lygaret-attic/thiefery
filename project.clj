(defproject thiefery "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojure-lanterna "0.9.4"]]
  :main ^:skip-aot thiefery.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
