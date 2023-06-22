(ns com.tasks.components.config
  (:require
    [com.fulcrologic.fulcro.server.config :as fserver]
    [mount.core :refer [defstate args]]
    ))

(defstate config
  :start (let [{:keys [config overrides]
                :or   {config "config/dev.edn"}} (args)
               loaded-config (merge (fserver/load-config! {:config-path config}) overrides)]
           loaded-config))