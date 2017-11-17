; java -cp clojure-1.8.0.jar clojure.main src/s06_code_golf_1.clj ../pride-and-prejudice.txt
(doseq [[w c] (take 25
                (sort-by val >
                  (frequencies
                    (remove (set (.split (slurp "../stop_words.txt") ","))
                            (re-seq #"[a-z]{2,}" (.toLowerCase (slurp (first *command-line-args*))))))))]
  (println w "-" c))
