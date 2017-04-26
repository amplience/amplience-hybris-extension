package ampliencedmaddon.script

import com.amplience.hybris.dm.util.ExportImages
import org.apache.commons.io.FileUtils

import java.nio.file.Paths

def outputFolderPath = Paths.get(FileUtils.getTempDirectoryPath(), 'amplience', 'export')

ExportImages amplienceExportImages = spring.getBean('amplienceExportImages')

amplienceExportImages.catalogCode = 'apparelProductCatalog'
amplienceExportImages.catalogVersion = 'Online'
amplienceExportImages.imageFormats = ["superZoom", "zoom", "product"]
amplienceExportImages.query = """
SELECT DISTINCT tbl.pk, tbl.code FROM (
{{ SELECT {pk} AS pk, {code} AS code FROM {ApparelStyleVariantProduct!} WHERE {catalogVersion} = ?catalogVersion }}
UNION
{{ SELECT {pk} AS pk, {code} AS code FROM {ApparelProduct!} WHERE {catalogVersion} = ?catalogVersion AND {variantType} IS NULL }}
) tbl ORDER BY tbl.code
"""
amplienceExportImages.doExport(outputFolderPath)
