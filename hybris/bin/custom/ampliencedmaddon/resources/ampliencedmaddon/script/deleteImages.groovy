package ampliencedmaddon.script

import com.amplience.hybris.dm.util.DeleteImages

DeleteImages amplienceDeleteImages = spring.getBean('amplienceDeleteImages')

amplienceDeleteImages.catalogCode = 'apparelProductCatalog'
amplienceDeleteImages.catalogVersion = 'Staged'
amplienceDeleteImages.doDeleteImages()

amplienceDeleteImages.catalogVersion = 'Online'
amplienceDeleteImages.doDeleteImages()
