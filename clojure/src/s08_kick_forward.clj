; java -cp clojure-1.8.0.jar:src clojure.main -m s08-kick-forward ../pride-and-prejudice.txt

(ns s08-kick-forward
  (:refer-clojure :exclude [frequencies sort]))

(declare normalize scan remove-stop-words frequencies sort
         print-text no-op)

(defn read-file [path-to-file func]
  (let [data (slurp path-to-file)]
    (func data normalize)))

(defn filter-chars [str-data func]
  (func (.replaceAll str-data "[\\W_]+" " ") scan))

(defn normalize [str-data func]
  (func (.toLowerCase str-data) remove-stop-words))

(defn scan [str-data func]
  (func (.split str-data " ") frequencies))

(defn remove-stop-words [word-list func]
  (func (remove (set (concat (.split (slurp "../stop_words.txt") ",")
                             (map (comp str char) (range (int \a) (inc (int \z))))))
                word-list)
        sort))

(defn frequencies [word-list func]
  (func (loop [ws word-list wf {}]
          (if-let [w (first ws)]
            (recur (rest ws) (update wf w (fnil inc 0)))
            wf))
        print-text))

(defn sort [wf func]
  (func (sort-by val > wf) no-op))

(defn print-text [word-freqs func]
  (func (doseq [[w c] (take 25 word-freqs)]
          (println w "-" c))))

(defn no-op [func])

(defn -main [filename]
  (read-file filename filter-chars))
