package mochico.dev.example.sharingandroidq

class Target(val name: String) {
    val icon: Int
        get() = R.mipmap.ic_launcher

    companion object {

        /**
         * The list of dummy targets.
         */
        private val TARGET = arrayOf(
            Target("Ale"),
            Target("Lager"),
            Target("Pilsner"),
            Target("Stout"),
            Target("Porter"),
            Target("Bock"),
            Target("Weissbier"),
            Target("Lambic"),
            Target("Kölsch"),
            Target("Malt Liquor")
        )

        const val INVALID_ID = -1

        fun byId(id: Int): Target {
            return TARGET[id]
        }
    }
}
