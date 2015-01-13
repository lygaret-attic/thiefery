(ns thiefery.ui
  (:require [lanterna.screen :as screen]))

(defrecord State [name])

(defmulti run 
  "Executes whatever code takes place at this state, including drawing, 
   side effects and input, and returns a new state stack."
  :name)
