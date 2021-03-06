SUMMARY = "Qt plugin for mapplauncherd"
HOMEPAGE = "https://git.merproject.org/mer-core/mapplauncherd-qt"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://qtbooster/qtbooster.cpp;beginline=1;endline=18;md5=fb70bd5bb640878875111d8161fa303c"

SRC_URI = "git://git.merproject.org/mer-core/mapplauncherd-qt.git;protocol=https \
          file://booster-qt5.service"
SRCREV = "8c2d34f64c7e21bcf394e937e71361da51e6077c"
PR = "r1"
PV = "+git${SRCPV}"
S = "${WORKDIR}/git"
inherit qmake5

DEPENDS += "qtdeclarative qtbase mapplauncherd polkit"
RDEPENDS_${PN} += "polkit mapplauncherd"

do_configure_prepend() {
    sed -i "s@INCLUDEPATH += /usr/include/applauncherd@INCLUDEPATH += ${STAGING_INCDIR}/applauncherd@" ${S}/qtbooster/qtbooster.pro
    cp ${WORKDIR}/booster-qt5.service ${S}/data/booster-qt5.service
}

do_install_append() {
    rm ${D}/usr/lib/systemd/user/booster-qt5-signal.service
    install -d ${D}/home/ceres/.config/systemd/user/default.target.wants/
    if [ ! -f ${D}/home/ceres/.config/systemd/user/default.target.wants/booster-qt5.service ]; then
        ln -s /usr/lib/systemd/user/booster-qt5.service ${D}/home/ceres/.config/systemd/user/default.target.wants/booster-qt5.service
    fi
}

FILES_${PN} += "/usr/lib/systemd/user /usr/libexec/mapplauncherd /home/ceres/.config/systemd/user/default.target.wants/"
FILES_${PN}-dev += "/usr/share/mkspecs/features"
FILES_${PN}-dbg += "/usr/libexec/mapplauncherd/.debug"
