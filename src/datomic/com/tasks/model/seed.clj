(ns com.tasks.model.seed)

(defn new-item
  "Seed helper. Uses street at db/id for tempid purposes."
  [id name status dificulty & {:as extras}]
  (merge
    {:db/id      name
     :item/id    id
     :item/name  name
     :item/status status
     :item/dificulty dificulty
     }
    extras))