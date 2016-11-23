# Example using Yahoo Weather Java API
woeids = { London: '44418', Szeged: '815498' }
woeids.map do |city, woeid|
  channel = @weather_service.get_forecast(woeid, DegreeUnit::CELSIUS)
  channel.item.forecasts.map do |forecast|
    {
      location: "#{channel.location.city}, #{channel.location.country}",
      day: forecast.day,
      temperature: "#{forecast.low} / #{forecast.high}",
      description: forecast.text
    }
  end
end
