# Example using Yahoo Weather Java API
results = []
woeids = { london: '44418', szeged: '815498' }
woeids.each do |city, woeid|
  channel = weatherService.getForecast(woeid, DegreeUnit::CELSIUS)
  channel.item.forecasts.each do |forecast|
    results.push({
      location: "#{channel.location.city}, #{channel.location.country}",
      day: forecast.day,
      temperature: "#{forecast.low} / #{forecast.high}",
      description: forecast.text
    })
  end
end
return results
