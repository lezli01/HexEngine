currTime = os.time()

print( currTime )

function timeout()
	
	elapsed = ( os.time() - currTime )
	
	if  elapsed >= 5000 then
		
		error( "timeout" )
    
	end
	
end


function startAi()
  
  dofile( "hex.engine/scripts/ai/ai.lua" )

end

debug.sethook( timeout, "", 100 )
startAi()