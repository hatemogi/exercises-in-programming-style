; java -cp clojure-1.8.0.jar:src clojure.main -m s09-the-one ../pride-and-prejudice.txt
(ns s09-the-one
  (:refer-clojure :exclude [frequencies sort]))

(def read-file slurp)

(defn filter-chars [str-data]
  (.replaceAll str-data "[\\W_]+" " "))

(defn normalize [str-data]
  (.toLowerCase str-data))

(defn scan [str-data]
  (.split str-data " "))

(defn remove-stop-words [word-list]
  (let [stop-words (.split (slurp "../stop_words.txt") ",")
        lower-cases (map (comp str char) (range (int \a) (inc (int \z))))
        stop-words-set (set (concat stop-words lower-cases))]
    (remove stop-words-set word-list)))

(defn frequencies [word-list]
  (loop [ws word-list wf {}]
    (if-let [w (first ws)]
      (recur (rest ws) (update wf w (fnil inc 0)))
      wf)))

(defn sort [word-freqs]
  (sort-by val > word-freqs))

(defn top25-freqs [word-freqs]
  (reduce (fn [s [w c]] (str s w " - " c "\n"))
          ""
          (take 25 word-freqs)))

(defn bind [v f]
  (f v))

(defn -main [filename]
  (print (bind filename              (fn [n]
         (bind (read-file n)         (fn [s]
         (bind (filter-chars s)      (fn [s]
         (bind (normalize s)         (fn [s]
         (bind (scan s)              (fn [w]
         (bind (remove-stop-words w) (fn [w]
         (bind (frequencies w)       (fn [f]
         (bind (sort f)              (fn [f]
         (bind (top25-freqs f)       identity)))))))))))))))))))
