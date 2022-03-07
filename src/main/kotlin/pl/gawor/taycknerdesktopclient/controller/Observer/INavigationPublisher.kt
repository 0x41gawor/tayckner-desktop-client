package pl.gawor.taycknerdesktopclient.controller.Observer


open class INavigationPublisher {
    //---// S U B S C R I B E R S
    private val subscribers: ArrayList<INavigationSubscriber> = ArrayList()

    //---// S U B S C R I B E
    fun subscribe(s: INavigationSubscriber) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: INavigationSubscriber) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    fun button_dayPlannerOnAction() {
        for (s in subscribers) {
            s.button_dayPlannerOnAction()
        }
    }
    fun button_DayTrackerOnAction() {
        for (s in subscribers) {
            s.button_DayTrackerOnAction()
        }
    }
    fun button_habitTrackerOnAction() {
        for (s in subscribers) {
            s.button_habitTrackerOnAction()
        }
    }
}