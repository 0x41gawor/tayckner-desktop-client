package pl.gawor.taycknerdesktopclient.controller.Observer

//---// S U B S C R I B E R
interface ISubscriber<Model> {
    //---// U P D A T E
    fun update(model: Model)
}