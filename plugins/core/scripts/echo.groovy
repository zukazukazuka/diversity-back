target ( stuff : 'A target to do some stuff.' ) {
    println ( 'Stuff' )
    echo ( message : 'A default message from Ant.' )
    echo ( message : "echo for args ${args}")
    echo ( message : "echo for options ${options}")
}

target (help : "Help for this Script"){
    echo (message : "this script is sample Script")
}

setDefaultTarget ('stuff')
