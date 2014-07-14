g = GameTable;
a = Action;
m = g:map();

if( g:units():size() > 0 )then

  moved = false;

  for ui = 0, g:units():size()-1, 1 do

    unit = g:units():get(ui);
  
    if( unit:buildings():size() > 0 and math.random( 2 ) == 1 and g:buildings():size() == 0 )then
    
      a:build( unit:x() - 1, unit:y(), unit, unit:buildings():get(0) );
      
    end
    
    if( g:buildings():size() > 0 and math.random( 2 ) == 1 )then
    
      b = g:buildings():get(0);
  
      if( b:constructing() == false )then
      
        for var = 0, b:produces():size()-1, 1 do
        
          p = b:produces():get(var);
          
          if( p:producing() == false)then
          
            a:produce(p,b);
          
          end
        
        end
      
      end
    
    end
  
    if( g:enemies():size() > 0 )then
    
      for var=0, g:enemies():size()-1, 1 do
    
        enemy = g:enemies():get(var);
        
        skill = unit:skills():get(0);

        for si = 0, unit:skills():size()-1, 1 do
          if( unit:skills():get(si):cooldown()==0)then
            skill = unit:skills():get(si);
          end
        end
        
        if( ( g:distance(unit,enemy) <= skill:range() ) and ( skill:cooldown() == 0 ) )then
          a:skill(unit,enemy:x(),enemy:y(),skill);
        end
        
      end
  
    end
  
    if( unit:speed() > 0 )then
  
      x = unit:x();
      y = unit:y();
  
      repeat
    
        if ( math.random( 2 ) == 1 ) then
          if ( math.random(2) == 1 ) then
            x = x + 1;
          else
            x = x - 1;
          end
        else
          if ( math.random(2) == 1 ) then
            y = y + 1;
          else
            y = y - 1;
          end
        end
      
      until ( m:isTherePath( unit, x, y ) == true )
    
      moved = true;
      a:move( unit, x, y );
      
    end
  
  end
  
  if( moved == false )then
    a:endTurn();
  end
  
else

  a:endTurn();

end