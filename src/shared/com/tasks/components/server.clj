(ns com.tasks.components.server
  (:require
    [org.httpkit.server :refer [run-server]]
    [mount.core :as mount :refer [defstate]]
    [com.tasks.components.config :refer [config]]
    [com.tasks.components.ring-middleware :refer [middleware]]))

(defstate http-server
  :start
  (let [cfg     (get config :org.httpkit.server/config)
        stop-fn (run-server middleware cfg)]
    {:stop stop-fn})
  :stop
  (let [{:keys [stop]} http-server]
    (when stop
      (stop))))

(defn -main [& args]
  (mount/start-with-args {:config "config/prod.edn"}))