(ns mutt.core)

(defprotocol Rangeable
  (prange [end exc?] [start end exc?] [start end step exc?]))

(extend-type Number
  Rangeable
  (prange
   ([end exc?] (prange 0 end 1 exc?))
   ([start end exc?] (prange start end 1 exc?))
   ([start end step exc?]
      (lazy-seq
       (let [end (if exc? end (inc end))]
         (loop [acc [] cur start]
           (if (< cur end)
             (recur (conj acc cur ) (+ cur step))
             acc)))))))

(extend-type Character
  Rangeable
  (prange
   ([end exc?] (prange (Character/MIN_VALUE) end 1 exc?))
   ([start end exc?] (prange start end 1 exc?))
   ([start end step exc?]
      (map char (prange (int start) (int end) step exc?)))))

(defn range
  ([] (prange 0 (Double/POSITIVE_INFINITY) 1 true))
  ([end] (prange end true))
  ([start end] (prange start end 1 true))
  ([start end step] (prange start end step true))
  ([start end step exclusive?] (prange start end step exclusive?)))