; java -cp clojure-1.8.0.jar:src clojure.main -m s07-infinite-mirror ../pride-and-prejudice.txt
(ns s07-infinite-mirror)

(defn wf-count [words stopwords wordfreqs]
  (if (empty? words)
    wordfreqs
    (let [word (first words)
          freq' (if (contains? stopwords word)
                   wordfreqs
                   (update wordfreqs word (fnil inc 0)))]
      (recur (rest words) stopwords freq'))))

(defn wf-print [wordfreq]
  (when-let [pair (first wordfreq)]
    (println (pair 0) "-" (pair 1))
    (recur (rest wordfreq))))

(defn -main [filename]
  (let [stopwords (set (.split (.trim (slurp "../stop_words.txt")) ","))
        words (re-seq #"[a-z]{2,}" (.toLowerCase (slurp filename)))
        freqs (wf-count words stopwords {})]
    (wf-print (sort-by second > freqs))))
