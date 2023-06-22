(ns com.tasks.components.datomic
  (:require
    [com.fulcrologic.rad.database-adapters.datomic-cloud :as datomic]
    [datomic.client.api :as d]
    [mount.core :refer [defstate]]
    [com.tasks.model :refer [all-attributes]]
    [com.tasks.components.config :refer [config]]))

(defstate ^{:on-reload :noop} datomic-connections
  :start
  (datomic/start-databases all-attributes config))

(comment
  (let [c (:main datomic-connections)
        db (d/db c)]
    (d/q '[:find (pull ?a [*])
           :where
           [?a :account/id]]
      db))

  )
