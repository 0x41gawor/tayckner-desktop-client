package pl.gawor.taycknerdesktopclient.controller.Observer

interface IPublisher<Model> {

    //---// S U B S C R I B E R S
    val subscribers: ArrayList<ISubscriber<Model>>

    //---// S U B S C R I B E
    fun subscribe(s: ISubscriber<Model>) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: ISubscriber<Model>) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    fun notifySubscribers()
}