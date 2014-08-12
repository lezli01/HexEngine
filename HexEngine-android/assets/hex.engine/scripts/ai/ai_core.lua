currTime = os.time()

print( currTime )

function timeout()
	
	elapsed = ( os.time() - currTime )
	
	if  elapsed >= 5000 then
		
		error( "timeout" )
  	Action:endTurn()
    
	end
	
end

debug.sethook( timeout, "", 100 )
dofile( "hex.engine/scripts/ai/ai.lua" )