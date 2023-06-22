(ns development
  (:require
    [clojure.tools.namespace.repl :as tools-ns :refer [set-refresh-dirs]]
    [com.tasks.components.datomic :refer [datomic-connections]]
    [com.tasks.components.ring-middleware]
    [com.tasks.components.server]
    [com.tasks.model.seed :as seed]
    [com.fulcrologic.rad.ids :refer [new-uuid]]
    [datomic.client.api :as d]
    [mount.core :as mount]
    [taoensso.timbre :as log]))

(set-refresh-dirs "src/main" "src/datomic" "src/dev" "src/shared")


(defn seed! []
  (let [connection (:main datomic-connections)]
    (when connection
      (log/info "SEEDING data.")
      (d/transact connection {:tx-data
                              [
                               (seed/new-item (new-uuid 200) "Try a new hobby or activity that challenges you." false "Easy"
                                 )
                               (seed/new-item (new-uuid 201) "Take a spontaneous adventure or trip to a new place" false "Easy"
                                 )
                               (seed/new-item (new-uuid 202) "Volunteer for a cause you’re passionate about" true "Hard"
                                 )
                               (seed/new-item (new-uuid 203) "Host a vision board party with friends to set and share goals" false "Easy"
                                 )
                               (seed/new-item (new-uuid 204) "Take a dance or cooking class to learn a new skill" false "Easy"
                                 )
                               (seed/new-item (new-uuid 205) "Write a letter to your future self about your hopes and dreams" false "Easy"
                                 )
                               (seed/new-item (new-uuid 206) "Create a “success jar” and fill it with notes of your accomplishments throughout the yea" false "Easy"
                                 )
                               ]})))
      )

(defn start []
  (mount/start-with-args {:config "config/dev.edn"})
  (seed!)
  :ok)

(defn cli-start "Start & seed the app from the CLI using `clojure -X ..`" [_] (start))

(defn stop
  "Stop the server."
  []
  (mount/stop))

(def go start)

(defn restart
  "Stop, refresh, and restart the server."
  []
  (stop)
  (tools-ns/refresh :after 'development/start))

(def reset #'restart)

