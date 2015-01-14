(ns thiefery.core
  (:require [thiefery.state :as state]
            [lanterna.screen :as screen]))

(defrecord Game [layout])

(defmethod state/draw :boot [state scr]
  (screen/put-string scr 10 10 "Welcome to THIEFERY.")
  (screen/put-string scr 10 11 "A game of thiefing, adventure and glory.")
  (screen/put-string scr 10 14 "Choose a, b or c."))

(defn- handle-char [state c]
  (-> state
    (update-in [:input (symbol (str c))] not)
    (assoc-in [:name] :play)))

(defn- return-state [value stack]
  (let [x   (peek stack)
        x'  (assoc-in x [:params :return] value)]
    (conj (pop stack ) x')))

(defmethod state/draw :read-string [state scr]
  (let [[_, height] (screen/get-size scr)
        y           (- height 1)
        prompt      (get-in state [:params :prompt])
        echo        (get-in state [:params :return])]
    (screen/put-string scr 0 y (str prompt " " echo))))

(defn- is-printable-char? [c]
  (and (char? c) (<= 32 (int c) 137)))

(defmethod state/run :read-string [state scr stack]
  (let [c (screen/get-key-blocking scr)]
    (cond 
      
      (is-printable-char? c)
      (conj stack (update-in state [:params :return] #(str % c)))

      (and (= c :backspace))
      (conj stack (update-in state [:params :return] #(.substring % 0 (max (- (.length %) 1) 0))))

      (= c :enter)
      (return-state (get-in state [:params :return]) stack)

      :else
      (conj stack state))))

(defmethod state/run :boot [state scr stack]
  (conj stack 
        (state/make-state :play) 
        (state/make-state :read-string {:prompt "Your name?"})))

(defmethod state/draw :play [state scr]
  (when-let 
    [answer (get-in state [:params :return])]
    (screen/put-string scr 10 10 "You said")
    (screen/put-string scr 10 11 (str \" answer \"))
    (screen/put-string scr 10 14 "Press any key.")))

(defmethod state/run :play [state scr stack]
  (screen/get-key-blocking scr)
  stack)

(defn- game-loop [scr boot]
  (loop [stack boot]
    (when (seq stack)
      (screen/clear scr)
      (doseq [x stack] (state/draw x scr))
      (screen/redraw scr)
      (recur (state/run (peek stack) scr (pop stack))))))

(defn- main [type]
  (let [scr (screen/get-screen type { :font "DejaVu Sans Mono" })]
    (screen/in-screen scr
      (game-loop scr [(state/make-state :boot)])

(defn -main [& args]
  (let [args (set args)
        type (cond
               (args "swing") :swing
               (args "text")  :text
               :else          :auto)]
    (main type)))
